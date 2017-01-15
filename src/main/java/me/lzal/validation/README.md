** Validation task **

Let's say that we have a following data on the input that has format:

|Name      |Surname   |Age   |ZipCode    |
|characters|characters|number|\d\d-\d\d\d|

.. it does not have any meaning at the moment.
The important thing about is the format. We have:
- two character field 'name' and 'surname'
- a numerical field 'Age' and 
- the 'ZipCode' which has its own specific format.

The task is to validate incoming data against the above format and log all lines of that that does not conform to it.

To be more concrete below are the acceptance criteria that we need to fullfill.

** Acceptance Criteria **

The following conditions are checked. If a certain item does not agree with a condition, it is excluded from further processing and this event is logged for this process.

| Condition                                                   | Log                                                            |
| ----------------------------------------------------------- | -------------------------------------------------------------- |
| The item agrees with the expected format (see format above) | UserFeed#\<line number in original feed>: Incorrect item format |
| Value is not empty:                                         | UserFeed#\<line number in original feed>: Missing <field name>  |
| * Name                                                      |                                                                |
| * Surname                                                   |                                                                |
