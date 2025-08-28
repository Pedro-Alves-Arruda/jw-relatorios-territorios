import { HttpInterceptorFn } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from '../AuthService';

export const authInterceptorInterceptor: HttpInterceptorFn = (req, next) => {
  
  const user = new AuthService(new Router).getUsuarioLogado();
    
    if (user.token) {
        const cloned = req.clone({
            headers: req.headers.set("Authorization", "Bearer " + user.token)
        });
        return next(cloned);
    }
  
  return next(req);
};
