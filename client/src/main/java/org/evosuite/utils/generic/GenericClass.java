package org.evosuite.utils.generic;

import org.evosuite.ga.ConstructionFailedException;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Interface for a generic class representation.
 *
 * All instantiate of this interface should be generated by {@code GenericClassFactory}
 * in order to ensure only one implementation is used at runtime.
 */
public interface GenericClass<T extends GenericClass<T>> {

    T self();

    /**
     * Determine if there exists an instantiation of the type variables such that the class matches
     * otherType
     *
     * @param otherType is the class we want to generate
     * @return whether this generic class can be instantiated to the given type.
     */
    boolean canBeInstantiatedTo(GenericClass<?> otherType);

    /**
     * <p>
     * changeClassLoader
     * </p>
     *
     * @param loader a {@link java.lang.ClassLoader} object.
     */
    void changeClassLoader(ClassLoader loader);

    // TODO: Why is this unused? Maybe we can remove this functionality / move it to a Utility function...
    /**
     * Get the boxed type of the represented generic class, if it is a primitive type.
     * If not, the rawClass is returned.
     *
     * @return Boxed type for primitive types, otherwise the raw class.
     */
    Class<?> getBoxedType();

    /**
     * The name of the represented generic class.
     * See {@see Class#getName()} for the exact format of the name.
     *
     * @return the name of the raw class.
     */
    String getClassName();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    GenericClass<?> getComponentClass();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    String getComponentName();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    Type getComponentType();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    Collection<GenericClass<?>> getGenericBounds();

    /**
     * Instantiate all type variables randomly, but adhering to type boundaries.
     *
     * @return The generic class with randomly set type variables.
     * @throws ConstructionFailedException If the generic class couldn't be constructed.
     */
    GenericClass<?> getGenericInstantiation() throws ConstructionFailedException;

    /**
     * Instantiate type variables using map.
     *
     * All type variables not contained in {@param map} are set randomly.
     *
     * @param typeMap map of type variables that MUST be a given type.
     * @return The generic class with type variables set as previously specified.
     * @throws ConstructionFailedException If the generic class couldn't be constructed.
     */
    GenericClass<?> getGenericInstantiation(Map<TypeVariable<?>, Type> typeMap) throws ConstructionFailedException;

    // TODO: write proper documentation.
    /**
     *
     * @param typeMap
     * @param recursionLevel
     * @return
     * @throws ConstructionFailedException
     */
    GenericClass<?> getGenericInstantiation(Map<TypeVariable<?>, Type> typeMap, int recursionLevel) throws ConstructionFailedException;

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    List<T> getInterfaces();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    int getNumParameters();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    T getOwnerType();

    /**
     * A list containing the generic type parameters (as {@code Type} objects) of the represented generic class.
     *
     * The order of the list is equal to the order of the generic parameters (from left to right).
     *
     * @return a view of the described list.
     */
    List<Type> getParameterTypes();

    /**
     * A list containing the generic type parameters (as {@code GenericClass} objects) of the represented generic class.
     *
     * The order of the list is equal to the order of the generic parameters (from left to right).
     *
     * @return a view of the described list.
     */
    List<GenericClass<?>> getParameterClasses();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    Class<?> getRawClass();

    // TODO: Why is this unused? Maybe we can remove this functionality / move it to a Utility function...
    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    Type getRawComponentClass();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    String getSimpleName();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    T getSuperClass();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    Type getType();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    String getTypeName();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    Map<TypeVariable<?>, Type> getTypeVariableMap();

    /**
     * Return a list of type variables of this type, or an empty list if this is not a parameterized
     * type
     *
     * @return a view of the list.
     */
    List<TypeVariable<?>> getTypeVariables();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    Class<?> getUnboxedType();

    // TODO: write proper documentation.
    /**
     *
     * @param componentClass
     * @return
     */
    GenericClass<?> getWithComponentClass(GenericClass<?> componentClass);

    // TODO: Why is this unused? Maybe we can remove this functionality / move it to a Utility function...
    // TODO: write proper documentation.
    /**
     *
     * @param parameters
     * @return
     */
    GenericClass<?> getWithGenericParameterTypes(List<T> parameters);

    // TODO: write proper documentation.
    /**
     *
     * @param superClass
     * @return
     * @throws ConstructionFailedException
     */
    GenericClass<?> getWithParametersFromSuperclass(GenericClass<?> superClass) throws ConstructionFailedException;

    // TODO: write proper documentation.
    /**
     *
     * @param parameters
     * @return
     */
    GenericClass<?> getWithParameterTypes(List<Type> parameters);

    // TODO: write proper documentation.
    /**
     *
     * @param parameters
     * @return
     */
    GenericClass<?> getWithParameterTypes(Type[] parameters);

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    GenericClass<?> getWithWildcardTypes();

    // TODO: write proper documentation.
    /**
     *
     * @param superType
     * @return
     */
    boolean hasGenericSuperType(GenericClass<?> superType);

    // TODO: Why is this unused? Maybe we can remove this functionality / move it to a Utility function...
    // TODO: write proper documentation.
    /**
     *
     * @param superType
     * @return
     */
    boolean hasGenericSuperType(Type superType);

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean hasOwnerType();

    /**
     * Check if the represented generic class contains any type parameters
     * or wildcards.
     *
     * For inner classes, the type parameters and wildcards of the owner type
     * are also considered.
     *
     * If the represented generic class is a array type,
     * the method call is delegated to the component class of the array.
     *
     * @return Whether the generic class has any type parameter or wildcard
     */
    boolean hasWildcardOrTypeVariables();

    /**
     * Checks if this generic class contains or is a type variables.
     *
     * If this generic class is a parameterized type,
     * all parameter types are checked recursively for type variables.
     *
     * E.g. A<B<T>> returns true, if T is a type variable
     *
     * @return Whether type has a type variable.
     */
    boolean hasTypeVariables();


    /**
     * Checks if this generic class contains or is a wildcard types.
     *
     * If this generic class is a parameterized type,
     * all parameter types are checked recursively for wildcards.
     *
     * E.g. A<B<?>> returns true.
     *
     * @return Whether this generic class contains or is a wildcard type.
     */
    boolean hasWildcardTypes();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isAbstract();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isAnonymous();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isArray();

    // TODO: write proper documentation.
    /**
     *
     * @param rhsType
     * @return
     */
    boolean isAssignableFrom(GenericClass<?> rhsType);

    // TODO: write proper documentation.
    /**
     *
     * @param rhsType
     * @return
     */
    boolean isAssignableFrom(Type rhsType);

    // TODO: write proper documentation.
    /**
     *
     * @param lhsType
     * @return
     */
    boolean isAssignableTo(GenericClass<?> lhsType);

    // TODO: write proper documentation.
    /**
     *
     * @param lhsType
     * @return
     */
    boolean isAssignableTo(Type lhsType);

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isClass();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isEnum();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isGenericArray();

    // TODO: write proper documentation.
    /**
     *
     * @param subType
     * @return
     */
    boolean isGenericSuperTypeOf(GenericClass<?> subType);

    // TODO: Why is this unused? Maybe we can remove this functionality / move it to a Utility function...
    // TODO: write proper documentation.
    /**
     *
     * @param subType
     * @return
     */
    boolean isGenericSuperTypeOf(Type subType);

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isObject();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isParameterizedType();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isPrimitive();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isRawClass();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isTypeVariable();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isWildcardType();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isString();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isVoid();

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    boolean isWrapperType();

    // TODO: write proper documentation.
    /**
     *
     * @param typeVariable
     * @return
     */
    boolean satisfiesBoundaries(TypeVariable<?> typeVariable);

    // TODO: write proper documentation.
    /**
     *
     * @param typeVariable
     * @param typeMap
     * @return
     */
    boolean satisfiesBoundaries(TypeVariable<?> typeVariable, Map<TypeVariable<?>, Type> typeMap);

    // TODO: write proper documentation.
    /**
     *
     * @param wildcardType
     * @return
     */
    boolean satisfiesBoundaries(WildcardType wildcardType);

    // TODO: write proper documentation.
    /**
     *
     * @param wildcardType
     * @param typeMap
     * @return
     */
    boolean satisfiesBoundaries(WildcardType wildcardType, Map<TypeVariable<?>, Type> typeMap);

    // TODO: write proper documentation.
    /**
     *
     * @return
     */
    GenericClass<?> getRawGenericClass();

    // TODO: write proper documentation.
    /**
     *
     * @param typeMap
     * @param recursionLevel
     * @return
     * @throws ConstructionFailedException
     */
    GenericClass<?> getGenericWildcardInstantiation(Map<TypeVariable<?>, Type> typeMap, int recursionLevel) throws ConstructionFailedException;
}
