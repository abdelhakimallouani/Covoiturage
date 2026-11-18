package ma.youcode.covoiturage.common.handler;

import jakarta.servlet.http.HttpServletRequest;
import ma.youcode.covoiturage.common.exception.DuplicateResourceException;
import ma.youcode.covoiturage.common.exception.ResourceNotFoundException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

        private ProblemDetail createProblem(
                        HttpStatus status,
                        String message,
                        HttpServletRequest request) {
                ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                                status,
                                message);

                problem.setTitle(status.getReasonPhrase());
                problem.setType(URI.create(
                                "https://api.covoiturage.ma/errors/" + status.value()));

                problem.setProperty("timestamp", Instant.now());
                problem.setProperty("code", status.value());
                problem.setProperty("path", request.getRequestURI());

                return problem;
        }

        // 404 - Ressource introuvable
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ProblemDetail> handleNotFound(
                        ResourceNotFoundException ex,
                        HttpServletRequest request) {
                ProblemDetail problem = createProblem(
                                HttpStatus.NOT_FOUND, ex.getMessage(), request);

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
        }

        // 409 - Ressource déjà existante
        @ExceptionHandler(DuplicateResourceException.class)
        public ResponseEntity<ProblemDetail> handleDuplicate(
                        DuplicateResourceException ex,
                        HttpServletRequest request) {
                ProblemDetail problem = createProblem(
                                HttpStatus.CONFLICT, ex.getMessage(), request);

                return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
        }

        // 400 - Erreurs de validation DTO
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ProblemDetail> handleValidation(
                        MethodArgumentNotValidException ex,
                        HttpServletRequest request) {
                String errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                                .collect(Collectors.joining(", "));

                ProblemDetail problem = createProblem(
                                HttpStatus.BAD_REQUEST, errors, request);

                return ResponseEntity.badRequest().body(problem);
        }

        // 409 - Violation de contrainte en base de données
        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ProblemDetail> handleDataIntegrity(
                        DataIntegrityViolationException ex,
                        HttpServletRequest request) {
                ProblemDetail problem = createProblem(
                                HttpStatus.CONFLICT,
                                "Une donnée existe déjà ou viole une contrainte en base de données.",
                                request);

                return ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
        }

        // 500 - Erreur inattendue
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ProblemDetail> handleInternalError(
                        Exception ex,
                        HttpServletRequest request) {
                ProblemDetail problem = createProblem(
                                HttpStatus.INTERNAL_SERVER_ERROR,
                                "Une erreur interne est survenue.",
                                request);

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problem);
        }
}
