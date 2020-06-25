import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, ResponseType, ResponseContentType } from '@angular/http';
import { AppStateService } from '../../shared/services/app-state.service';
import { PreloadService } from '../../shared/services/preload.service';
import { finalize } from "rxjs/operators";
import { Constants } from '../services/constants';

@Injectable()
export class HttpClient {

    constructor(private http: Http,
        private appState: AppStateService,
        private preload: PreloadService,
        private constants: Constants
    ) {
    }

    public createAuthorizationHeader(headers: Headers) {
        if (this.appState.accessToken) {
            headers.append('Authorization', `Bearer ${this.appState.accessToken}`);
        }
    }

    public get(url: string) {
        const headers = new Headers();
        this.createAuthorizationHeader(headers);
        return this.http.get(url,
            {
                headers: headers
            }
        );
    }

    public post(url: string, data: any, isSkip: boolean) {

        this.showLoader(isSkip);
        const headers = new Headers();
        this.createAuthorizationHeader(headers);

        const options = new RequestOptions({
            headers: headers
        });

        return this.http.post(url, data, options).pipe(
            finalize(
                () => this.hideLoader(isSkip)
            )
        );
    }

    public download(url: string, data: any, isSkip: boolean) {
        this.showLoader(isSkip);
        const headers = new Headers();
        this.createAuthorizationHeader(headers);

        const options = new RequestOptions({
            headers: headers,
            responseType: ResponseContentType.Blob
        });

        return this.http.post(url, data, options).pipe(
            finalize(
                () => this.hideLoader(isSkip)
            )
        );
    }

    public upload(url: string, formData: FormData, isSkip: boolean) {
        this.showLoader(isSkip);
        const headers = new Headers();
        this.createAuthorizationHeader(headers);

        const options = new RequestOptions({
            headers: headers,
            responseType: ResponseContentType.Blob
        });

        return this.http.post(url, formData, options).pipe(
            finalize(
                () => this.hideLoader(isSkip)
            )
        );
    }

    private showLoader(isSkip: boolean): void {
        if (!isSkip) {
            this.preload.show();
        }
    }

    private hideLoader(isSkip: boolean) {
        if (!isSkip) {
            setTimeout(() => {
                this.preload.hide();
            }, 500);
        }
    }
}
