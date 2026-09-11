package com.one.domain.temperature.service;

import com.one.domain.child.entity.Child;
import com.one.domain.child.exception.ChildErrorCode;
import com.one.domain.child.repository.ChildRepository;
import com.one.domain.device.entity.Device;
import com.one.domain.device.repository.DeviceRepository;
import com.one.domain.fever.entity.FeverEpisode;
import com.one.domain.fever.enums.FeverSeverity;
import com.one.domain.fever.enums.FeverStatus;
import com.one.domain.fever.repository.FeverRepository;
import com.one.domain.temperature.dto.request.TemperatureCreateRequest;
import com.one.domain.temperature.dto.response.CurrentTemperatureResponse;
import com.one.domain.temperature.dto.response.TemperatureHistoryResponse;
import com.one.domain.temperature.dto.response.TemperaturePointResponse;
import com.one.domain.temperature.entity.TemperatureLog;
import com.one.domain.temperature.mapper.TemperatureLogMapper;
import com.one.domain.temperature.repository.TemperatureLogRepository;
import com.one.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TemperatureLogService {

    private final TemperatureLogRepository temperatureLogRepository;
    private final ChildRepository childRepository;
    private final DeviceRepository deviceRepository;
    private final FeverRepository feverRepository;

    public CurrentTemperatureResponse createTemperatureLog(TemperatureCreateRequest request, Long childId) {

        // 시작 로깅
        log.info("[TemperatureLogService] 체온 기록 시작");

        // 아이부터 찾기
        Child child = childRepository.findById(childId).orElseThrow(() -> {
            log.warn("[TemperatureLogService] 존재하지 않는 아이입니다.");
            return new CustomException(ChildErrorCode.CHILD_NOT_FOUND);
        });

        // 체온 확인 및 현재 체온의 심각도 확인
        double temperature = request.getTemperature();
        FeverSeverity currentSeverity = FeverSeverity.from(temperature);

        // 아이의 현재 ongoingEpisode가 있는지 확인
        Optional<FeverEpisode> ongoingEpisodeOpt =
                feverRepository.findByChildAndStatus(child, FeverStatus.ONGOING);

        FeverEpisode targetEpisode = null;

        Device device = deviceRepository.findById(request.getDeviceId()).orElse(null);

        if (currentSeverity == null) {
            // Case 1: 정상 체온 진입
            ongoingEpisodeOpt.ifPresent(episode -> {
                episode.endEpisode(request.getMeasuredAt()); // status = ENDED, ended_at 기록
            });
        } else {
            if (ongoingEpisodeOpt.isPresent()) {
                // Case 3: 이미 발열 진행 중 -> 현재 측정된 심각도로 업데이트
                targetEpisode = ongoingEpisodeOpt.get();
                targetEpisode.updateSeverity(currentSeverity);
            } else {
                // Case 2: 새로 발열 시작 -> 신규 에피소드 생성
                targetEpisode = feverRepository.save(FeverEpisode.builder()
                        .child(child)
                        .status(FeverStatus.ONGOING)
                        .startedAt(request.getMeasuredAt())
                        .severity(currentSeverity)
                        .build());
            }
        }


        TemperatureLog temperatureLog = TemperatureLog.builder()
                .child(child)
                .device(device)
                .feverEpisode(targetEpisode)
                .temperature(temperature)
                .source(request.getTemperatureSource())
                .measurementSite(request.getMeasurementSite())
                .note(request.getNote())
                .measuredAt(request.getMeasuredAt())
                .build();

        Optional<TemperatureLog> previousLogOpt = temperatureLogRepository.findTopByChildOrderByMeasuredAtDesc(child);

        double tempDifference = 0.0;

        if (previousLogOpt.isPresent()) {
            double diff = temperature - previousLogOpt.get().getTemperature();
            tempDifference = Math.round(diff * 10.0) / 10.0; // 소수점 한 자리 반올림
        }

        TemperatureLog savedTemperatureLog = temperatureLogRepository.save(temperatureLog);

        // 만약 기기로 측정한 것이라면, 기기를 찾아와야함
//        if(request.getTemperatureSource() == TemperatureSource.DEVICE){
//            device = deviceRepository.findById(request.getDeviceId()).orElseThrow(() -> {
//                log.warn("[TemperatureLogService] 기기를 찾을 수 없습니다.");
//                return new CustomException(null); // TODO DEVICE에러코드 만들기
//            });
//
//        } else {
//            device = null;
//
//        }

        return TemperatureLogMapper.toTemperatureResponse(savedTemperatureLog, tempDifference);
    }

    public TemperatureHistoryResponse getTemperatureHistory(Long childId){

        // 아이 찾기
        Child child = childRepository.findById(childId).orElseThrow(() -> {
            log.warn("[TemperatureService] 아이를 찾을 수 없습니다.");
            return new CustomException(ChildErrorCode.CHILD_NOT_FOUND);
        });

        LocalDateTime basisTime =  LocalDateTime.now().minusHours(6);

        List<TemperatureLog> temperatureLogList = temperatureLogRepository.findByChildAndMeasuredAtGreaterThanEqualOrderByMeasuredAtAsc(child, basisTime);

        // 리스트의 마지막 요소를 가져와 현재 체온으로 설정
        Double currentTemperature = temperatureLogList.isEmpty() ? null
                : temperatureLogList.getLast().getTemperature();

        // 체온 목록이 비어있지 않은 경우 추출 (Optional 반환)
        Double maxTemperature = temperatureLogList.stream()
                .mapToDouble(TemperatureLog::getTemperature)
                .max()
                .orElse(0.0); // 데이터가 없을 경우 기본값 설정

        Double minTemperature = temperatureLogList.stream()
                .mapToDouble(TemperatureLog::getTemperature)
                .min()
                .orElse(0.0);

        List<TemperaturePointResponse> pointList = temperatureLogList.stream()
                .map(TemperatureLogMapper::toTemperaturePointResponse)
                .toList();

        TemperatureHistoryResponse temperatureHistoryResponse = TemperatureHistoryResponse.builder()
                .currentTemperature(currentTemperature)
                .maxTemperature(maxTemperature)
                .minTemperature(minTemperature)
                .points(pointList)
                .build();

        return temperatureHistoryResponse;
    }

    public CurrentTemperatureResponse getLatestCurrentTemperature(Long childId) {
        Child child = childRepository.findById(childId).orElseThrow(() -> {
            log.warn("[TemperatureService] 아이를 찾을 수 없습니다.");
            return new CustomException(ChildErrorCode.CHILD_NOT_FOUND);
        });

        TemperatureLog log = temperatureLogRepository.findTopByChildOrderByMeasuredAtDesc(child).orElse(null);

        Optional<TemperatureLog> previousLogOpt = temperatureLogRepository.findTopByChildOrderByMeasuredAtDesc(child);

        double tempDifference = 0.0;

        if (previousLogOpt.isPresent()) {
            double diff = log.getTemperature() - previousLogOpt.get().getTemperature();
            tempDifference = Math.round(diff * 10.0) / 10.0; // 소수점 한 자리 반올림
        }

        CurrentTemperatureResponse response = TemperatureLogMapper.toTemperatureResponse(log, tempDifference);

        return response;
    }
}
