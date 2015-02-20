/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotworld.service;

import java.util.Enumeration;
import java.util.Hashtable;


public class ServiceController {
    private Hashtable _services;
    public ServiceController(Hashtable services) {
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
     * starts a service and set variable first checking if
     * service exists, 
     * and then the service is not already running
     * @param serviceId id of service to start
     * @param data the threshold or sample rate of monitor
     */
    public void startService(int serviceId, int data) {
        Integer SID = Integer.valueOf(serviceId);
        if(!_services.containsKey(SID)) {
            return;
        } else if(((IService)_services.get(SID)).isScheduled()) {
            return;
        }
       ((IService)_services.get(SID)).getMonitor().setVariable(data);
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
     * for each serviceID in services table,
     *  for each seviceID in array
     *      if service 'X' is found in array and is not running
     *          start service 'X'
     *      if service 'X' does not appear in array and is running
     *          stop service 'X'
     */
     public void autoManageServices(int[] serviceIDs, int[] data, int[] dir)
    {
        IService service;
        boolean hasBeenFound;
        //for each serviceID
            //if service[id] is not running start it
        int i;
             System.out.println("AUTOMANAGING SERVICES");
        for(Enumeration e = this._services.keys();e.hasMoreElements(); )
        {
            hasBeenFound = false;
            service = (IService)this._services.get(Integer.valueOf(e.nextElement().toString()));
            for(i = 0;i<serviceIDs.length;i++) {
                //if e is found.
                if(service.getServiceId() == serviceIDs[i]){
                    hasBeenFound = true;
                    if(service.isScheduled()) {
                        service.setData(data[i]);
                        service.setDirection(dir[i]);
                    } else {
                        service.setData(data[i]);
                        service.setDirection(dir[i]);
                        service.startService();
                    }
                }
            }
            if(!hasBeenFound && service.isScheduled()) {
                service.stopService();
            }
        }
        System.out.println("------EXIT SERVICE LOOP----");
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
    
    
    public IService getService(int serviceId) {
        Integer ID = Integer.valueOf(serviceId);
        if(!this._services.contains(ID))
            return null;
        return (IService) this._services.get(ID);
    }
}
