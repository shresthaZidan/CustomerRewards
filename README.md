# Customer Rewards Calculator based on customer transaction
Assessment Prompt:

The rest API to get customer rewards based on customer Id

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every
dollar spent between $50 and $100 in each transaction.
(e.g., a $120 purchase = 2x$20 + 1x$50 = 90 points).  

EntryPoint: `CustomerRewardsApplication.java`.
Initial data load file: `script.sql`.
Run `mvn clean install` to build the application.

Exposed Endpoints are:
1. Use `v1/customers` to retrieve customer information
2. Use `v1/customers/{customerId}/rewards` to view the rewards 

Use the swagger Docs:
   ``http://localhost:8080/swagger-ui/index.htm``
