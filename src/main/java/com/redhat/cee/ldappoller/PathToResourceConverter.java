package com.redhat.cee.ldappoller;

import org.osgi.service.blueprint.container.Converter;
import org.osgi.service.blueprint.container.ReifiedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by abhiskum on 1/27/17.
 */
public class PathToResourceConverter implements Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PathToResourceConverter.class);

    @Override
    public boolean canConvert(Object object, ReifiedType reifiedType) {
        return object != null && object instanceof String && Resource.class.getName().equalsIgnoreCase(reifiedType.getRawClass().getName());
    }

    @Override
    public Object convert(Object object, ReifiedType reifiedType) throws Exception {
        return new ClassPathResource(object.toString()) {
            public InputStream getInputStream() throws IOException {
                InputStream is;
                ClassLoader classLoader = this.getClass().getClassLoader();
                String path = getPath();
                LOGGER.info("Convert for: " + path);
                if (classLoader != null) {
                	LOGGER.info("Convert for: " + path);
                	is = classLoader.getResourceAsStream(path);
                } else {
                	LOGGER.info("Convert system for: " + path);
                    is = ClassLoader.getSystemResourceAsStream(path);
                }

                if (is == null) {
                    throw new FileNotFoundException(getDescription() + " cannot be opened because it does not exist" + path);
                }

                return is;
            }
        };
    }

}
