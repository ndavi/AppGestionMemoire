import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import { Account, LoginModalService, Principal } from '../shared';
import {HomeService} from "./home.service";
import {Tag, TagService} from "../entities/tag";
import {HttpErrorResponse, HttpResponse} from "@angular/common/http";

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    files : FileList;
    model: any[] = [];
    tags: Tag[];

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private homeService: HomeService,
        private tagService: TagService,
        private jhiAlertService: JhiAlertService
    ) {
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
        });
        this.tagService.query()
            .subscribe((res: HttpResponse<Tag[]>) => { this.tags = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    onUploadFilesSubmit(files: any) {
        this.files = files.target[0].files;
        this.model = [];
        console.log(this.files.length);
        for (let i = 0; i < this.files.length; i++) {
            this.model.push({})
        }
    }

    onLastFormSubmit() {
        for (let i = 0; i < this.files.length; i++) {
            let reader = new FileReader();
            reader.readAsDataURL(this.files[i]);
            this.model[i].extension = this.files[i].name.split(".").pop();
            reader.onload = (e) => {
                this.model[i].base64Data = reader.result;
            };
            reader.onerror = function (error) {
                console.log('Error: ', error);
            };
        }
        this.homeService.uploadMemoire(this.model).subscribe(res => {
            console.log(res);
        })
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    getBase64(file) : any {

    }
}
