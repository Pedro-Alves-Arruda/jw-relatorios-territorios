import { Routes } from '@angular/router';
import { PublicadorCadastroComponent } from './Components/Publicador/publicador-cadastro/publicador-cadastro.component';
import { GrupoCampoCadastroComponent } from './Components/GrupoCampo/grupo-campo-cadastro/grupo-campo-cadastro.component';
import { MenuComponent } from './Components/Menu/menu/menu.component';
import { RevisitaCadastroComponent } from './Components/Revisitas/revisita-cadastro/revisita-cadastro.component';
import { LoginComponent } from './Components/Login/login/login.component';
import { RevisitasListarComponent } from './Components/Revisitas/revisitas-listar/revisitas-listar.component';
import { CanGuard } from './CanGuard';
import { CronometroComponent } from './Components/Campo/cronometro/cronometro.component';
import { FormularioRedefinicaoSenhaComponent } from './Components/Formularios/RedefinicaoSenha/formulario-redefinicao-senha/formulario-redefinicao-senha.component';
import { PerfilComponent } from './Components/Perfil/perfil/perfil.component';
import { DesignacaoCadastroComponent } from './Components/Designacao/designacao-cadastro/designacao-cadastro.component';

export const routes: Routes = [
    {
        path:'login', component: LoginComponent
    },
    {
        path: 'formulario-redefinicao-senha', component: FormularioRedefinicaoSenhaComponent
    },
    {
        path:'', component: MenuComponent, canActivate:[CanGuard] ,children: [
            {
                path: 'perfil', component: PerfilComponent, canActivate: [CanGuard]
            },
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
            },
            {
                path:'designacao-cadastro', component:DesignacaoCadastroComponent, canActivate: [CanGuard]
            }
        ]
    },
];
