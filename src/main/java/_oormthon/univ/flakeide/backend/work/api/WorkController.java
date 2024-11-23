package _oormthon.univ.flakeide.backend.work.api;

import _oormthon.univ.flakeide.backend.global.exception.CustomException;
import _oormthon.univ.flakeide.backend.training.api.dto.response.ListTrainingResDto;
import _oormthon.univ.flakeide.backend.training.api.dto.response.TrainingResDto;
import _oormthon.univ.flakeide.backend.work.api.dto.request.WorkCreateReqDto;
import _oormthon.univ.flakeide.backend.work.api.dto.response.ListWorkResDto;
import _oormthon.univ.flakeide.backend.work.api.dto.response.WorkResDto;
import _oormthon.univ.flakeide.backend.work.service.WorkService;
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
public class WorkController {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @PostMapping("/{courseId}/work")
    @Operation(summary = "과제(work) 등록")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 과제(work) 등록함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "404", description = "수업을 찾지 못함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
        @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    public ResponseEntity<String> createWork(@PathVariable("courseId") long courseId, @RequestBody WorkCreateReqDto workCreateReqDto) {
        workService.createWork(courseId, workCreateReqDto);
        System.out.println("controller: " + workCreateReqDto.title());
        return new ResponseEntity<>("과제(work) 등록", HttpStatus.CREATED);
    }

    @GetMapping("{courseId}/work")
    @Operation(summary = "수업의 과제(work) 목록 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 수업의 과제(work)의 목록 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ListTrainingResDto.class))),
        @ApiResponse(responseCode = "404", description = "수업을 찾지 못함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
        @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    public ResponseEntity<ListWorkResDto> getAllWorkOfCourse(@PathVariable("courseId") long courseId) {
        return new ResponseEntity<>(workService.getAllWorkOfCourse(courseId), HttpStatus.OK);
    }

    @GetMapping("{courseId}/work/{workId}")
    @Operation(summary = "수업의 과제(work) 상세 조회")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "성공적으로 수업의 과제(work)을 상세 조회함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TrainingResDto.class))),
        @ApiResponse(responseCode = "404", description = "수업을 찾지 못함", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class))),
        @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomException.class)))
    })
    public ResponseEntity<WorkResDto> getWorkDetail(@PathVariable("courseId") long courseId, @PathVariable("workId") long workId) {
      return new ResponseEntity<>(workService.getWorkDetail(courseId, workId), HttpStatus.OK);
    }
}
