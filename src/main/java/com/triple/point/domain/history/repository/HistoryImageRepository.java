package com.triple.point.domain.history.repository;

import com.triple.point.domain.history.HistoryImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryImageRepository extends JpaRepository<HistoryImage, String> {
}
