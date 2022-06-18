package com.bp.ruian.services.converter;

import com.bp.ruian.record.Address;
import org.elasticsearch.search.SearchHit;

/**
 * Interface for {@link Address} converting
 * @author denisprn
 */
public interface AddressConverter {

    Address convertFromArrayValues(String[] lineValues);

    Address convertFromSearchHit(SearchHit searchHit);
}
