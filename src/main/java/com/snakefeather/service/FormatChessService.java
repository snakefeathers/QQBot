package com.snakefeather.service;

import com.snakefeather.domain.Operation;

import java.io.IOException;

public interface FormatChessService {

    public Operation formatString(String userNews) throws IOException;
}
