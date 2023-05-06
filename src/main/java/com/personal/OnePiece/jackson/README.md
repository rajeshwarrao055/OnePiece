# Description
Jackson Documentation and Code local testing

### When is a field serialized/Deserialized
* Simplest way - make field public
* Alternate - add getter on non-public field 
  * Getter makes a non public field serializable and deserializable
* Setter makes a non-public field deserializable only
* At the object mapper level
```
ObjectMapper mapper = new ObjectMapper();
mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
```