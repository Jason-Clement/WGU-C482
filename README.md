# WGU-C482
 
This was a school project for building an inventory management application. Below are some notes I compiled for the evaluator after completing it:

--------------------------------------------------------------------------------
  What the requirements specify
--------------------------------------------------------------------------------

Parts & Products
- inventory level cannot be lower than the minimum
- minimum level cannot be higher than the maximum
- maximum level cannot be lower than the minimum

Products
- must always have at least one associated part
- cannot be deleted if it has an associated part
    - the two above requirements are conflicting and if implemented as-is, it
      would be impossible to delete a product since modifying and deleting happen
      on two different screens
    - the FAQ states that a product may be deleted after confirmation even if
      parts are associated with it
- price cannot be less than the sum of its parts
- name must have a value
- price must have a value
- inventory must have a value
- inventory defaults to 0

Miscellaneous
- all delete and cancel actions require a confirmation dialog


--------------------------------------------------------------------------------
  What the requirements do not specify
--------------------------------------------------------------------------------

Parts & Products
- whether the min and max fields can be lower or higher than the current
  inventory level when being set
    - logically, it follows that the min cannot be set higher than the current
      inventory level and that the max cannot be set lower than the level
      since the inventory level must fall between min and max
    - however, that is not explicitely specified
    - Since this will be handled on a single screen effectively removing any
      chance that the min or max will be set independently, I'm going to ignore
      this and just be sure to set the inventory level after the min and max
      have been set
    - Ideally, this should be handled in the base classes, but for the purposes
      of this project, I'm going to ignore this ideal

Parts
- whether it can be deleted if it is associated with a product
- whether any fields are mandatory

Products
- whether min and max are mandatory
- whether a part can be associated with it more than once


--------------------------------------------------------------------------------
  Assumptions I've Made
--------------------------------------------------------------------------------

it's generally a bad idea to make assumptions, but for the sake of completeness,
I've done just that about missing specifications

Parts & Products
- assume that the min/max are optional
    - when they are not specified, no check will required when setting the
      inventory level

Parts
- assume that the requirements document intends for a part to also have
  mandatory name, price, and inventory fields, because:
    - logically a part should have a name if a product should have one
    - I suppose parts could be referenced (by humans) by id alone, but that 
      does not seem to be the intent since it is auto-generated
    - price will default to 0 automatically, so that particular requirement
      is met, regardless
    - since a part can also have specified min/max fields, it makes sense that
      the inventory should also be mandatory or should default to 0
    - inventory will default to 0, anyway
- assume that a part cannot be deleted if it is associated with a product
    - You'd end up with null references otherwise
    - Logically, you wouldn't want that to happen because it would invisibly
      modify a product's cost and the user wouldn't see those changes at the
      time of deletion or even which products are affected

Products
- assume that a part cannot be associated with it more than once
    - the word "association" is used instead of something like "component" and
      there is no way to specify a quantity for each associated part, 
      so it follows that a part can be associated only once


--------------------------------------------------------------------------------
  Liberties I've Taken
--------------------------------------------------------------------------------

- I made the following deviations from the UML diagram:
    - quite a few added properties and methods and whatnot
    - used ObservableLists instead of ArrayLists
    - since some of the functionality for both part and product are identical,
      I had them inherit from an Item base class to cut down on copy-paste code
      (hello DRY principle!)
        - I allowed this based on the following text in the instructions:
              "The UML Class Diagram may be altered so long as the aspects of the
              current UML diagram are intact"
        - the aspects remain intact in that the resulting classes still contain
          the correct fields
    - I have no idea what I'm supposed to do with the update methods that only
      have one int parameter, so I've implemented those methods, but left them
      blank
    - the inconsistent removeProduct and deletePart method names are really
      bothering me, but I've forced myself to leave them be
        - just putting that out there
        - seriously, fix this please
- I only implemented one screen for product editing and part editing
    - the add/edit screens are identical except for the title so why duplicate
      them? (DRY principle, again)
    - the title still shows "Update" when modifying an existing product/part
      and "Add" when adding a new product/part
- no JavaDoc comments
- no unit testing
