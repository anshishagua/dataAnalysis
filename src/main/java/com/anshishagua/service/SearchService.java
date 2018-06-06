package com.anshishagua.service;

import com.anshishagua.object.Index;
import com.anshishagua.object.Table;
import com.anshishagua.object.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/6/1
 * Time: 下午10:32
 */

@Service
public class SearchService {
    @Autowired
    private TableService tableService;
    @Autowired
    private IndexService indexService;
    @Autowired
    private TagService tagService;

    public List<Table> searchByTable(String query) {
        Objects.requireNonNull(query);

        return tableService.getByNameLike(query);
    }

    public List<Index> searchByIndex(String query) {
        Objects.requireNonNull(query);

        return indexService.getByNameLike(query);
    }

    public List<Tag> searchByTag(String query) {
        Objects.requireNonNull(query);

        return tagService.getByNameLike(query);
    }
}