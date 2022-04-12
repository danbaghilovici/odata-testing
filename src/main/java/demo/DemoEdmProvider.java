package demo;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.*;
import org.apache.olingo.commons.api.ex.ODataException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DemoEdmProvider extends CsdlAbstractEdmProvider {

    // Service Namespace
    public static final String NAMESPACE="OData.Demo";

    // EDM Conteiner
    public static final String CONTAINER_NAME="Container";
    public static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE,CONTAINER_NAME);

    // Entity Types Names
    public static final String ET_PRODUCT_NAME="Product";
    public static final FullQualifiedName ET_PRODUCT_FQN = new FullQualifiedName(NAMESPACE, ET_PRODUCT_NAME);

    // Entity Set Names
    public static final String ES_PRODUCTS_NAME = "Products";

    public DemoEdmProvider() {
        super();
    }

    @Override
    public CsdlEnumType getEnumType(FullQualifiedName enumTypeName) throws ODataException {
        return super.getEnumType(enumTypeName);
    }

    @Override
    public CsdlTypeDefinition getTypeDefinition(FullQualifiedName typeDefinitionName) throws ODataException {
        return super.getTypeDefinition(typeDefinitionName);
    }

    @Override
    public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) throws ODataException {
        // this method is called for one of the EntityTypes that are configured in the Schema
        if(entityTypeName.equals(ET_PRODUCT_FQN)){

            //create EntityType properties
            CsdlProperty id = new CsdlProperty().setName("ID").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
            CsdlProperty name = new CsdlProperty().setName("Name").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            CsdlProperty  description = new CsdlProperty().setName("Description").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());

            // create CsdlPropertyRef for Key element
            CsdlPropertyRef propertyRef = new CsdlPropertyRef();
            propertyRef.setName("ID");

            // configure EntityType
            CsdlEntityType entityType = new CsdlEntityType();
            entityType.setName(ET_PRODUCT_NAME);
            entityType.setProperties(Arrays.asList(id, name , description));
            entityType.setKey(Collections.singletonList(propertyRef));

            return entityType;
        }

        return null;
    }

    @Override
    public CsdlComplexType getComplexType(FullQualifiedName complexTypeName) throws ODataException {
        return super.getComplexType(complexTypeName);
    }

    @Override
    public List<CsdlAction> getActions(FullQualifiedName actionName) throws ODataException {
        return super.getActions(actionName);
    }

    @Override
    public List<CsdlFunction> getFunctions(FullQualifiedName functionName) throws ODataException {
        return super.getFunctions(functionName);
    }

    @Override
    public CsdlTerm getTerm(FullQualifiedName termName) throws ODataException {
        return super.getTerm(termName);
    }

    @Override
    public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) throws ODataException {
        if(entityContainer.equals(CONTAINER)){
            if(entitySetName.equals(ES_PRODUCTS_NAME)){
                CsdlEntitySet entitySet = new CsdlEntitySet();
                entitySet.setName(ES_PRODUCTS_NAME);
                entitySet.setType(ET_PRODUCT_FQN);

                return entitySet;
            }
        }

        return null;
    }

    @Override
    public CsdlSingleton getSingleton(FullQualifiedName entityContainer, String singletonName) throws ODataException {
        return super.getSingleton(entityContainer, singletonName);
    }

    @Override
    public CsdlActionImport getActionImport(FullQualifiedName entityContainer, String actionImportName) throws ODataException {
        return super.getActionImport(entityContainer, actionImportName);
    }

    @Override
    public CsdlFunctionImport getFunctionImport(FullQualifiedName entityContainer, String functionImportName) throws ODataException {
        return super.getFunctionImport(entityContainer, functionImportName);
    }

    @Override
    public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) throws ODataException {
        // This method is invoked when displaying the Service Document at e.g. http://localhost:8080/DemoService/DemoService.svc
        if (entityContainerName == null || entityContainerName.equals(CONTAINER)) {
            CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
            entityContainerInfo.setContainerName(CONTAINER);
            return entityContainerInfo;
        }

        return null;
    }

    @Override
    public List<CsdlAliasInfo> getAliasInfos() throws ODataException {
        return super.getAliasInfos();
    }

    @Override
    public List<CsdlSchema> getSchemas() throws ODataException {
        // create Schema
        CsdlSchema schema = new CsdlSchema();
        schema.setNamespace(NAMESPACE);

        // add EntityTypes
        List<CsdlEntityType> entityTypes = new ArrayList<CsdlEntityType>();
        entityTypes.add(getEntityType(ET_PRODUCT_FQN));
        schema.setEntityTypes(entityTypes);

        // add EntityContainer
        schema.setEntityContainer(getEntityContainer());

        // finally
        List<CsdlSchema> schemas = new ArrayList<CsdlSchema>();
        schemas.add(schema);

        return schemas;
    }

    @Override
    public CsdlEntityContainer getEntityContainer() throws ODataException {
        // create EntitySets
        List<CsdlEntitySet> entitySets = new ArrayList<CsdlEntitySet>();
        entitySets.add(getEntitySet(CONTAINER, ES_PRODUCTS_NAME));

        // create EntityContainer
        CsdlEntityContainer entityContainer = new CsdlEntityContainer();
        entityContainer.setName(CONTAINER_NAME);
        entityContainer.setEntitySets(entitySets);

        return entityContainer;
    }

    @Override
    public CsdlAnnotations getAnnotationsGroup(FullQualifiedName targetName, String qualifier) throws ODataException {
        return super.getAnnotationsGroup(targetName, qualifier);
    }
}
