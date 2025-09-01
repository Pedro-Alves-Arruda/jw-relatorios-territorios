import { Routes } from '@angular/router';
import { PublicadorCadastroComponent } from './Components/Publicador/publicador-cadastro/publicador-cadastro.component';
import { GrupoCampoCadastroComponent } from './Components/GrupoCampo/grupo-campo-cadastro/grupo-campo-cadastro.component';
import { MenuComponent } from './Components/Menu/menu/menu.component';
import { RevisitaCadastroComponent } from './Components/Revisitas/revisita-cadastro/revisita-cadastro.component';
import { LoginComponent } from './Components/Login/login/login.component';
import { RevisitasListarComponent } from './Components/Revisitas/revisitas-listar/revisitas-listar.component';
import { CanGuard } from './CanGuard';
import { CronometroComponent } from './Components/Campo/cronometro/cronometro.component';

export const routes: Routes = [
    {
        path:'login', component: LoginComponent
    },
    {
        path:'', component: MenuComponent, children: [
            {
               path: 'publicador-cadastro',  component: PublicadorCadastroComponent, canActivate: [CanGuard]
            },
            {
               path: 'grupo-campo-cadastro',  component: GrupoCampoCadastroComponent, canActivate: [CanGuard]
            },
            {
                path: 'revisita-cadastro', component: RevisitaCadastroComponent, canActivate: [CanGuard]
            },
            {
                path: 'revisita-listar', component: RevisitasListarComponent, canActivate: [CanGuard]
            },
            {
                path: 'campo/cronometro', component: CronometroComponent, canActivate: [CanGuard]
            }
        ]
    }
];
