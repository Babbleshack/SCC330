/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotword.data.service;

import java.util.Enumeration;
import java.util.Hashtable;


public class ServiceController {
    private Hashtable _services;
    ServiceController(Hashtable services) {
        this._services = services;
    }
    /**
     * starts all services
     */
    public void innitializeServices() {
        IService service;
        for(Enumeration e = this._services.keys(); e.hasMoreElements() ; )
        {
             service = (IService) _services.get(e);
             service.startService();
        }
    }
    /**
     * starts a service first checking if
     * service exists, 
     * and then the service is not already running
     * @param serviceId id of service to start
     */
    public void startService(int serviceId) {
        Integer SID = Integer.valueOf(serviceId);
        if(!_services.containsKey(SID))
            return;
        else if(((IService)_services.get(SID)).isScheduled())
            return;
        else
            ((IService)_services.get(SID)).startService();
    }
    /**
     * stops a service, first checking
     * service exists,
     * an then that the service is not already stopped
     * @param serviceId id of service to be stopped
     */
    public void stopService(int serviceId) {
        Integer SID = Integer.valueOf(serviceId);
        if(!_services.containsKey(SID))
            return;
        else if(!((IService)_services.get(SID)).isScheduled())
            return;
        else
            ((IService)_services.get(SID)).stopService();
    }
    /**
     * adds a new service to current service pool
     * @param service 
     */
    public void addService(IService service) {
        if(this._services.containsKey(Integer.valueOf(service.getServiceId())))
            return;
        this._services.put(Integer.valueOf(service.getServiceId()), service);
    }
    /**
     * removes service from current service pool
     * @param serviceId service to remove
     * @return IService removed service
     */
    public IService removeService(int serviceId) {
        Integer ID = Integer.valueOf(serviceId);
        if(!this._services.contains(ID))
            return null;
        return (IService) this._services.remove(ID);
    }
}
