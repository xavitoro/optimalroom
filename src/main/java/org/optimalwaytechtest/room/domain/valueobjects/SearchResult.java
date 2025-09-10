package org.optimalwaytechtest.room.domain.valueobjects;

import java.util.List;

public record SearchResult<T>(List<T> items, long total) {}
