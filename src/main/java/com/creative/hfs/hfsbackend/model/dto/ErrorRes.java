package com.creative.hfs.hfsbackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorRes {
	
    HttpStatus httpStatus;
    String message;

}
