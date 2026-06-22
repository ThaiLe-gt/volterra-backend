package com.dt.files.adapter.in.web;

import com.dt.common.api.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
class FileController {
    @PostMapping({"/api/v1/files/upload", "/common/files/upload", "/manage/files"})
    ApiResponse<Map<String, Object>> upload() {
        return ApiResponse.accepted("file upload scaffold", Map.of("provider", "LOCAL_OR_S3"));
    }

    @GetMapping({"/api/v1/files", "/common/files/list", "/manage/files/list"})
    ApiResponse<List<Map<String, Object>>> list() {
        return ApiResponse.success(List.of());
    }

    @GetMapping({"/api/v1/files/download", "/common/files/download", "/manage/files/download"})
    ApiResponse<Map<String, Object>> download() {
        return ApiResponse.accepted("file download scaffold", Map.of());
    }

    @DeleteMapping({"/api/v1/files", "/common/files/remove", "/manage/files/{id}"})
    ApiResponse<Void> delete() {
        return ApiResponse.success();
    }

    @GetMapping({"/api/v1/files/presign", "/common/files/presign"})
    ApiResponse<Map<String, Object>> presign() {
        return ApiResponse.success(Map.of("url", "presign scaffold"));
    }
}
