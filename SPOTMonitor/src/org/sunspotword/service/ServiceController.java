/**
 *
 * @author Dominic Lindsay
 */
package org.sunspotword.service;

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
    public void autoStartService(int[] serviceIDs, int[] data) {
        IService service = null;
        for(int i = 0; i < serviceIDs.length; i++) {
            if(!this._services.containsKey(Integer.valueOf(serviceIDs[i]))){
                continue;
            }
            service = (IService)this._services.get(Integer.valueOf(serviceIDs[i]));
            if(service.isScheduled()) {
                service.getMonitor().setVariable(data[i]);
            } else {
                service.getMonitor().setVariable(data[i]);
                service.startService();
            }
            
        }
    }
    /**
     * for each service,
     *  for each serviceID
     *      if service id does not appear in array
     *          stop service
     */
    public void autoStopService(int[] serviceIDs)
    {
        IService service;
        for(Enumeration e = this._services.keys(); e.hasMoreElements() ; ){
           for(int i=0; i<serviceIDs.length; i++){
               
               service = (IService) this._services.get(Integer.valueOf(i));
               System.out.println("service ID autostoping: " + serviceIDs[i] );
               System.out.println("Service ID of retrieved Service: " +  service.getServiceId());
               if(service == null) {
                   System.out.println("Null value");
                   continue;
               }
            if(service.getServiceId() != serviceIDs[i] ) {
                this.stopService(i);
            }
          }
        }
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
