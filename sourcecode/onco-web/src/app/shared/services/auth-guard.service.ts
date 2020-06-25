import { Injectable } from '@angular/core';
import { Router, CanActivate, CanActivateChild } from '@angular/router';
import { AppStateService } from './app-state.service';

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {
    constructor(public router: Router,
        public appState: AppStateService) { }

    canActivate(): boolean {
        if (this.appState.isLoggedIn) {
            return true;
        }

        this.router.navigate(['/login']);
        return false;
    }

    canActivateChild(): boolean {
        if (this.appState.isLoggedIn) {
            return true;
        }

        this.router.navigate(['/login']);
        return false;
    }
}
