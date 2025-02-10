package com.theroom.server.domain.request;

import lombok.Data;

import java.util.List;

@Data
public class MainContentRequest {
    List<String> uploadImageFilenames;
}
