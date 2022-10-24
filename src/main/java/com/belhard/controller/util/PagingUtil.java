package com.belhard.controller.util;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.Data;

@Component
public class PagingUtil {

	public PagingUtil() {
	}

	public Paging getPaging(@RequestParam int limit, @RequestParam long page) {
		if (limit <= 0 || limit > 50) {
			limit = 10;
		}
		if (page < 0) {
			page = 1;
		}
		long offset = (page - 1) * limit;
		return new Paging(limit, offset, page);
	}

	public long getTotalPages(long totalEntities, int limit) {
		long totalPages = totalEntities / limit;
		int additionalPage = (totalEntities - (totalPages * limit) > 0) ? 1 : 0;
		return totalPages + additionalPage;
	}

	@Data
	public static class Paging {
		private final int limit;
		private final long offset;
		private final long page;
	}
}
