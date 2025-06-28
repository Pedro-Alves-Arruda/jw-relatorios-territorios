import { Routes } from '@angular/router';
import { PublicadorCadastroComponent } from './Components/Publicador/publicador-cadastro/publicador-cadastro.component';
import { GrupoCampoCadastroComponent } from './Components/GrupoCampo/grupo-campo-cadastro/grupo-campo-cadastro.component';
import { MenuComponent } from './Components/Menu/menu/menu.component';
import { RevisitaCadastroComponent } from './Components/Revisitas/revisita-cadastro/revisita-cadastro.component';
import { LoginComponent } from './Components/Login/login/login.component';
import { RevisitasListarComponent } from './Components/Revisitas/revisitas-listar/revisitas-listar.component';

export const routes: Routes = [
    {
        path:'login', component: LoginComponent
    },
    {
        path:'', component: MenuComponent, children: [
            {
               path: 'publicador-cadastro',  component: PublicadorCadastroComponent
            },
            {
               path: 'grupo-campo-cadastro',  component: GrupoCampoCadastroComponent
            },
            {
                path: 'revisita-cadastro', component: RevisitaCadastroComponent
            },
            {
                path: 'revisita-listar', component: RevisitasListarComponent
            }
        ]
    }
];
