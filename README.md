# labforward
## Repository layer

I used maps for repository purpose. I have category, item and category-item maps.

Category entity consists of attribute definition and id, attribute definition is used as key.

Item entity consists of attribute definition, id, title and description. Id is the key.

Category-item map keeps attribute definition as the key and list of items as value.

## Assumptions

For Creating items in those categories functionality, I assume that only one item can be created and it is also added to category-item map when created.

For Updating items, I assume that an item can be updated with title and description fields.

## Request

Creating categories with attribute definitions -> post request to http://localhost:8080/categories with the following request:

`{
    "attributeDefinition": "myCategory"
 }`

Creating items in those categories -> post request to http://localhost:8080/categories/myCategory/items with the following request:

`{
     "title": "title",
     "description": "description"
 }`
 
Updating items -> patch request to http://localhost:8080/items/{itemId} with the following request:

`{
     "title": "titleUpdated",
     "description": "descriptionUpdated"
 }`
 
Getting items of a category -> get request to http://localhost:8080/categories/myCategory/items
