package com.fasterxml.jackson.databind;

import java.util.*;

import com.fasterxml.jackson.core.type.JavaType;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedConstructor;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.util.Annotations;


/**
 * Basic container for information gathered by {@link ClassIntrospector} to
 * help in constructing serializers and deserializers.
 * Note that the main implementation type is
 * {@link com.fasterxml.jackson.databind.introspect.BasicBeanDescription},
 * meaning that it is safe to upcast to this type.
 * 
 * @author tatu
 */
public abstract class BeanDescription
{
    /*
    /**********************************************************
    /* Configuration
    /**********************************************************
     */

    /**
     * Bean type information, including raw class and possible
     * * generics information
     */
    protected final JavaType _type;

    /*
    /**********************************************************
    /* Life-cycle
    /**********************************************************
     */

    protected BeanDescription(JavaType type)
    {
    	_type = type;
    }

    /*
    /**********************************************************
    /* Simple accesors
    /**********************************************************
     */

    /**
     * Method for accessing declared type of bean being introspected,
     * including full generic type information (from declaration)
     */
    public JavaType getType() { return _type; }

    public Class<?> getBeanClass() { return _type.getRawClass(); }

    public abstract AnnotatedClass getClassInfo();
    
    public abstract boolean hasKnownClassAnnotations();

    /**
     * Accessor for type bindings that may be needed to fully resolve
     * types of member object, such as return and argument types of
     * methods and constructors, and types of fields.
     */
    public abstract TypeBindings bindingsForBeanType();

    /**
     * Method for resolving given JDK type, using this bean as the
     * generic type resolution context.
     * 
     * @since 1.9
     */
    public abstract JavaType resolveType(java.lang.reflect.Type jdkType);
    
    /**
     * Method for accessing collection of annotations the bean
     * class has.
     * 
     * @since 1.7
     */
    public abstract Annotations getClassAnnotations();
    
    /*
    /**********************************************************
    /* Basic API for finding properties, related
    /**********************************************************
     */
    
    /**
     * @return Ordered Map with logical property name as key, and
     *    matching getter method as value.
     *    
     * @since 1.9
     */
    public abstract List<BeanPropertyDefinition> findProperties();

    /**
     * @since 1.9
     */
    public abstract Map<Object, AnnotatedMember> findInjectables();
    
    /**
     * @since 1.9
     */
    public abstract AnnotatedMethod findAnyGetter();

    /**
     * @since 1.9
     */
    public abstract AnnotatedMethod findAnySetter();

    /**
     * @since 1.9
     */
    public abstract AnnotatedMethod findJsonValueMethod();

    /**
     * @since 1.9
     */
    public abstract AnnotatedConstructor findDefaultConstructor();
    
    /**
     * @since 1.9
     */
    public abstract Set<String> getIgnoredPropertyNames();

    /*
    /**********************************************************
    /* Deprecated methods
    /**********************************************************
     */

    /**
     * @deprecated Since 1.9 use {@link #findProperties}
     */
    @Deprecated
    public abstract LinkedHashMap<String,AnnotatedMethod> findGetters(VisibilityChecker<?> visibilityChecker,
            Collection<String> ignoredProperties);

    /**
     * @deprecated Since 1.9 use {@link #findProperties}
     */
    @Deprecated
    public abstract LinkedHashMap<String,AnnotatedMethod> findSetters(VisibilityChecker<?> visibilityChecker);

    /**
     * @deprecated Since 1.9 use {@link #findProperties}
     */
    @Deprecated
    public abstract LinkedHashMap<String,AnnotatedField> findDeserializableFields(VisibilityChecker<?> visibilityChecker,
            Collection<String> ignoredProperties);

    /**
     * @deprecated Since 1.9 use the non-deprecated version
     */
    @Deprecated
    public abstract Map<String,AnnotatedField> findSerializableFields(VisibilityChecker<?> visibilityChecker,
            Collection<String> ignoredProperties);

}
