import { Subject } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable()
export class AppStateService {

    constructor() { }

    private _data: any = {};
    private observer: Subject<any> = new Subject();
    private _clinicalData: any = {};
    private _variantCall: any = {};
    private _message: any = undefined;
    private _dxIdx: number;
    private _biospecimen: any = {};
    private _patient: any = {};
    private _breadcrumbs: any = {};
    private _sampleRnaSeq: any = {};
    private _dynamicComponents = {};
    private _userInfo: any = undefined;
    private _formData: any = undefined;

    public getObserver(): Subject<any> {
        return this.observer;
    }
    public updateObserver(param: any) {
        this.observer.next(param);
    }

    public get biospecimen(): any {
        return this._biospecimen;
    }
    public set biospecimen(value: any) {
        this._biospecimen = value;
    }

    public get data(): any {
        return this._data;
    }
    public set data(value: any) {
        this._data = value;
    }

    public get message(): any {
        return this._message;
    }
    public set message(value: any) {
        this._message = value;
    }

    public get clinicalData(): any {
        return this._clinicalData;
    }
    public set clinicalData(clinicalData: any) {
        this._clinicalData = clinicalData;
    }

    public get variantCall(): any {
        return this._variantCall;
    }
    public set variantCall(variantCall: any) {
        this._variantCall = variantCall;
    }

    public get dxIdx(): number {
        return this._dxIdx;
    }
    public set dxIdx(dxIdx: number) {
        this._dxIdx = dxIdx;
    }

    public get patient(): any {
        return  this._patient;
    }
    public set patient(patient: any) {
        this._patient = patient;
    }

    public get breadcrumbs(): any {
        return this._breadcrumbs;
    }
    public set breadcrumbs(breadcrumbs: any) {
        this._breadcrumbs = breadcrumbs;
    }

    public get sampleRnaSeq(): any {
        return this._sampleRnaSeq;
    }
    public set sampleRnaSeq(sampleRnaSeq: any) {
        this._sampleRnaSeq = sampleRnaSeq;
    }

    public get dynamicComponents(): any {
        return this._dynamicComponents;
    }
    public set dynamicComponents(dynamicComponents: any) {
        this._dynamicComponents = dynamicComponents;
    }

    // Get current diagnosis
    public get currentDx(): any {
        const diagnosisList = this.clinicalData.diagnosisList || [];
        if (diagnosisList.length === 0 || (diagnosisList.length > 0 && !diagnosisList[this.dxIdx])) {
            diagnosisList.push({});
        }
        return diagnosisList[this.dxIdx];
    }
    public set currentDx(dx: any) {
        this.clinicalData.diagnosisList[this.dxIdx] = dx;
    }

    public get accessToken(): string {
        if (this.userInfo) {
            return this.userInfo.accessToken;
        }
    }

    public get isLoggedIn(): boolean {
        if (this.userInfo) {
            return true;
        }
        return false;
    }

    public get userInfo(): any {
        return this._userInfo;
    }
    public set userInfo(value: any) {
        this._userInfo = value;
    }

    public get formData(): any {
        return this._formData;
    }
    public set formData(value: any) {
        this._formData = value;
    }
}
