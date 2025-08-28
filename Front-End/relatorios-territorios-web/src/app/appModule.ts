import { HTTP_INTERCEPTORS, HttpClient, provideHttpClient, withInterceptors } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { authInterceptorInterceptor } from "./interceptor/auth-interceptor.interceptor";



@NgModule({
    imports: [ FormsModule],
    exports: [],
    providers:[
        provideHttpClient(withInterceptors([authInterceptorInterceptor])),
    ],
    
})

export class appModule{

}