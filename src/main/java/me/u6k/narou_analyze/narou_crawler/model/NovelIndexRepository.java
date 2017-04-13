
package me.u6k.narou_analyze.narou_crawler.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NovelIndexRepository extends JpaRepository<NovelIndex, String> {

    @Query("select idx from NovelIndex where idx.searchDate = :searchDate")
    List<NovelIndex> findBySearchDate(Date searchDate);

}
