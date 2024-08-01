package uin.dto;

import java.util.Map;

public record ErrorsDTO(Map<String, String[]> errors) {
}
