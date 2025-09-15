import { HTTP_INTERCEPTORS, HttpClient, provideHttpClient, withInterceptors } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { authInterceptorInterceptor } from "./interceptor/auth-interceptor.interceptor";
import { NgChartsModule } from 'ng2-charts';
import { NgxEchartsModule } from 'ngx-echarts';





@NgModule({
    imports: [FormsModule, NgChartsModule,NgxEchartsModule.forRoot({
      echarts: () => import('echarts')
    })],
    exports: [],
    providers:[
        provideHttpClient(withInterceptors([authInterceptorInterceptor])),
    ],
    
})

export class appModule{

}