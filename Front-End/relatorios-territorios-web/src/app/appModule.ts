import { provideHttpClient } from "@angular/common/http";
import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";

@NgModule({
    imports: [ FormsModule,],
    exports: [],
    providers:[provideHttpClient()]
})

export class appModule{

}