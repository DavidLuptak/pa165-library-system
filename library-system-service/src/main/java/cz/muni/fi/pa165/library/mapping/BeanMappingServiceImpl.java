package cz.muni.fi.pa165.library.mapping;

import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Lenka (433591)
 * @version 12.11.2016
 */
@Service
public class BeanMappingServiceImpl implements BeanMappingService {

    @Inject
    private Mapper dozer;

    @Override
    public Mapper getMapper() {
        return dozer;
    }

    @Override
    public <T> T mapTo(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    @Override
    public <T> List<T> mapTo(Collection<?> sourceObjects, Class<T> destinationClass) {

        List<T> mappedCollection = new ArrayList<>();

        for (Object object : sourceObjects) {
            mappedCollection.add(dozer.map(object, destinationClass));
        }

        return mappedCollection;
    }
}
