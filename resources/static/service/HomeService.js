'use strict';
 
    App.service('HomeService', ['$http', '$q', function($http, $q){


    var REST_SERVICE_URI = '/customer';




    this.getDeviceList = function getDeviceList(){
        return $http.get(REST_SERVICE_URI + '/get_device_list')

    }

    this.getDevice = function getDevice(dev_hard_id){
        return $http.get(REST_SERVICE_URI + '/get_device/?dev_hard_id=' + dev_hard_id)
    }
  
    this.getPeripheralList = function getPeripheralList(dev_hard_id){
        return $http.get(REST_SERVICE_URI + '/get_peripherals/?dev_hard_id=' + dev_hard_id)
    }

    this.getPeripheral = function getPeripheral(dev_hard_id,per_hard_id){
        return $http.get(REST_SERVICE_URI+'/get_peripheral/?dev_hard_id='+dev_hard_id+'&per_hard_id'+per_hard_id)
    }

    this.peripheralRequest = function peripheralRequest(dev_hard_id,per_hard_id,perRequest){
        return $http.post(REST_SERVICE_URI+'/peripheral_request/?dev_hard_id='+dev_hard_id+'&per_hard_id='+per_hard_id,perRequest)
    }

    this.getCustomer = function getCustomer(){
        return $http.get(REST_SERVICE_URI)
    }

    this.updateCustomer = function updateCustomer(update){
        return $http.put(REST_SERVICE_URI+'/update' , update)
    }

    this.changePassword = function changePassword(update){
        return $http.put(REST_SERVICE_URI+'/change_password',update)
    }
    
    
    }]);