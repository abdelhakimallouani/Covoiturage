package ma.youcode.covoiturage.passager.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import ma.youcode.covoiturage.passager.dto.request.CreatePassagerRequest;
import ma.youcode.covoiturage.passager.dto.response.PassagerResponse;
import ma.youcode.covoiturage.passager.service.PassagerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/passengers")
@RequiredArgsConstructor
public class PassagerController {

    private final PassagerService passagerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassagerResponse createPassager(@Valid @RequestBody CreatePassagerRequest request) {
        return passagerService.createPassager(request);
    }

    @GetMapping
    public List<PassagerResponse> getAllPassagers() {

        return passagerService.getAllPassagers();
    }

    @GetMapping("/{id}")
    public PassagerResponse getPassagerById(@PathVariable Long id) {

        return passagerService.getPassagerById(id);
    }

    @GetMapping("/search")
    public ResponseEntity<PassagerResponse> getPassagerByEmail(@RequestParam String email) {
        return ResponseEntity.ok(passagerService.getPassagerByEmail(email));
    }
}
