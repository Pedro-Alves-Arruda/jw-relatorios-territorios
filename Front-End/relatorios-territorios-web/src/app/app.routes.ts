import { Routes } from '@angular/router';
import { PublicadorCadastroComponent } from './Components/Publicador/publicador-cadastro/publicador-cadastro.component';
import { GrupoCampoCadastroComponent } from './Components/GrupoCampo/grupo-campo-cadastro/grupo-campo-cadastro.component';
import { MenuComponent } from './Components/Menu/menu/menu.component';
import { RevisitaCadastroComponent } from './Components/Revisitas/revisita-cadastro/revisita-cadastro.component';

export const routes: Routes = [
    {
        path:'', component: MenuComponent, children: [
            {
               path: 'publicador',  component: PublicadorCadastroComponent
            },
            {
               path: 'grupo-campo',  component: GrupoCampoCadastroComponent
            },
            {
                path: 'revisita-cadastro', component: RevisitaCadastroComponent
            }
        ]
    }
];
