package com.bp.ruian.services.finder;

import com.bp.ruian.record.Address;

import java.util.List;

/**
 * Interface for {@link Address} finder
 * @author denisprn
 */
public interface AddressFinder {

    List<Address> find(String searchString);
}
