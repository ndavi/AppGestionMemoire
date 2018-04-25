import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Specialite } from './specialite.model';
import { SpecialiteService } from './specialite.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-specialite',
    templateUrl: './specialite.component.html'
})
export class SpecialiteComponent implements OnInit, OnDestroy {
specialites: Specialite[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private specialiteService: SpecialiteService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.specialiteService.query().subscribe(
            (res: HttpResponse<Specialite[]>) => {
                this.specialites = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSpecialites();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Specialite) {
        return item.id;
    }
    registerChangeInSpecialites() {
        this.eventSubscriber = this.eventManager.subscribe('specialiteListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
