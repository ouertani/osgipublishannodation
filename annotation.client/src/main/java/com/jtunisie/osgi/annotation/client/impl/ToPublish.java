package com.jtunisie.osgi.annotation.client.impl;

import com.jtunisie.osgi.annotation.Publish;
import com.jtunisie.osgi.annotation.client.IService;
import com.jtunisie.osgi.annotation.NamedInterface;
import com.jtunisie.osgi.annotation.Prop;

/**
 *
 * @author slim ouertani
 */
@Publish(id = "tunisie",
NamedInterfaces = {@NamedInterface(IService.class)},
    Props = {
    @Prop(key = "url", value = "/jtunisie"),
    @Prop(key = "publish", value = "hessian")
})
public class ToPublish implements IService {

    public ToPublish() {
        System.out.println("service published..");
    }
}
