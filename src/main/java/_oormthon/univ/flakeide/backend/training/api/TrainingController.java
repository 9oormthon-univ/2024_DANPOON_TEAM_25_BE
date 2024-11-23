package _oormthon.univ.flakeide.backend.training.api;

import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import _oormthon.univ.flakeide.backend.training.api.dto.request.TrainingCreateReqDto;
import _oormthon.univ.flakeide.backend.training.api.dto.response.ListTrainingResDto;
import _oormthon.univ.flakeide.backend.training.api.dto.response.TrainingResDto;
import _oormthon.univ.flakeide.backend.training.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService assignmentService) {
        this.trainingService = assignmentService;
    }

    @PostMapping("{courseId}/training")
    @Operation(summary = "실습(training) 등록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 실습(training) 등록함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "수업을 찾지 못함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
        @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    public ResponseEntity<String> createTraining(@PathVariable("courseId") long courseId,
                                                @RequestBody TrainingCreateReqDto trainingCreateReqDto) {
        trainingService.createTraining(courseId, trainingCreateReqDto);
        return new ResponseEntity<>("실습(training) 등록", HttpStatus.CREATED);
    }

    @GetMapping("{courseId}/training")
    @Operation(summary = "수업의 실습(training) 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 수업의 실습(training) 목록 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListTrainingResDto.class))),
        @ApiResponse(responseCode = "404", description = "수업을 찾지 못함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
        @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    public ResponseEntity<ListTrainingResDto> getAllTrainingOfCourse(@PathVariable("courseId") long courseId) {
        return new  ResponseEntity<>(trainingService.getAllTrainingOfCourse(courseId), HttpStatus.OK);
    }

    @GetMapping("{courseId}/training/{trainingId}")
    @Operation(summary = "수업의 실습(training) 상세 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 수업의 실습(training) 상세 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "수업을 찾지 못함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
        @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    public ResponseEntity<TrainingResDto> getTrainingDetail(@PathVariable("courseId") long courseId, @PathVariable("trainingId") long trainingId) {
        return new ResponseEntity<>(trainingService.getTrainingDetail(courseId, trainingId), HttpStatus.OK);
    }
}
