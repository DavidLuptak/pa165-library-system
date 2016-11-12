package cz.muni.fi.pa165.library.service;

import java.util.Collection;
import java.util.List;

import org.dozer.Mapper;

/**
 * @author Lenka (433591)
 * @version 12.11.2016
 */
public interface BeanMappingService {

    Mapper getMapper();

    <T> T mapTo(Object source, Class<T> destinationClass);

    <T> List<T> mapTo(Collection<?> sourceObjects, Class<T> destinationClass);
}
