package com.bp.RUIAN.parsers;


import java.util.List;

public interface BulkDataUploader<T> {
    void upload(List<T> dataList);
}
