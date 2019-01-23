'use strict';
 
    App.service('AutoProductsService', ['$http', '$q', function($http, $q){


    var REST_SERVICE_URI = '/customer';

    //http://192.168.1.3:8080/customer/get_vehicle_list
    this.getVehicleList = function getVehicleList(){
        return $http.get(REST_SERVICE_URI + '/get_vehicle_list')
    }
    
    //http://192.168.1.3:8080/customer/get_parts/?vehi_id=1    
    this.getPartList = function getPartList(vehi_id){
        return $http.get(REST_SERVICE_URI+'/get_parts/?vehi_id='+vehi_id)
    }
    
    //http://192.168.1.3:8080/customer/get_part/?vehi_id=1&part_id=3
    this.getPart = function getPart(vehi_id,part_id){
    	return $http.get(REST_SERVICE_URI+'/get_part/?vehi_id='+vehi_id+'&part_id'+part_id)
    }
    
    //http://192.168.1.3:8080/customer/get_all_vehicle_by_name/?vehicle_name=MAZDA
    this.getAllByName= function getAllByName(vehi_name){
     	return $http.get(REST_SERVICE_URI+'/get_all_vehicle_by_name/?vehicle_name='+vehi_name)
    }
    
    //http://192.168.1.3:8080/customer/get_all_vehicle_by_model/?vehicle_model=BONG VANN
    this.getAllByModel= function getAllByModel(vehi_model){
     	return $http.get(REST_SERVICE_URI+'/get_all_vehicle_by_model/?vehicle_model='+vehi_model)
    }
    
    }]);