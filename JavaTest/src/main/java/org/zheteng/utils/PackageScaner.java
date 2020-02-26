package org.zheteng.utils;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CommonsLog
public class PackageScaner implements ResourceLoaderAware {

    private static final String CLASSPATH_ALL_URL_PRIFIX = "classpath*:";
    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private static ResourceLoader resourceLoader;
    private static ResourcePatternResolver resolver;
    private static MetadataReaderFactory reader;

    static {
        resourceLoader = new DefaultResourceLoader();
        resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
        reader = new CachingMetadataReaderFactory(resolver);
    }

    public static List<Class<?>> scanClasses(String packagesToScan, Class<?> annotationToScan) {
        List<Class<?>> result = new ArrayList<>();
        String resourcePath = CLASSPATH_ALL_URL_PRIFIX +
                ClassUtils.convertClassNameToResourcePath(packagesToScan) + "/" +
                DEFAULT_RESOURCE_PATTERN;
        try {
            Resource[] resources = resolver.getResources(resourcePath);
            for (Resource r : resources) {
                MetadataReader mr = reader.getMetadataReader(r);
                if (mr.getAnnotationMetadata().hasAnnotation(
                        annotationToScan.getName())) {
                    result.add(Class.forName(
                            mr.getClassMetadata().getClassName()));
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            ExceptionUtils.getStackTrace(e);
        }
        return result;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

    }
}
