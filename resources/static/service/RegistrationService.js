'use strict';
 
    App.service('RegistrationService', ['$http', '$q', function($http, $q){
 
    var REST_SERVICE_URI = '/api/register';
 
 
    this.createCustomer = function createCustomer(customer) {
        return $http.post(REST_SERVICE_URI, customer);
    }
 
 
 
    }]);