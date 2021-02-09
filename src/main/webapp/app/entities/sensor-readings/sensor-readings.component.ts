import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISensorReadings } from 'app/shared/model/sensor-readings.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { SensorReadingsService } from './sensor-readings.service';
import { SensorReadingsDeleteDialogComponent } from './sensor-readings-delete-dialog.component';

@Component({
  selector: 'jhi-sensor-readings',
  templateUrl: './sensor-readings.component.html',
})
export class SensorReadingsComponent implements OnInit, OnDestroy {
  sensorReadings: ISensorReadings[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected sensorReadingsService: SensorReadingsService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.sensorReadings = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.sensorReadingsService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ISensorReadings[]>) => this.paginateSensorReadings(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.sensorReadings = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSensorReadings();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISensorReadings): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSensorReadings(): void {
    this.eventSubscriber = this.eventManager.subscribe('sensorReadingsListModification', () => this.reset());
  }

  delete(sensorReadings: ISensorReadings): void {
    const modalRef = this.modalService.open(SensorReadingsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.sensorReadings = sensorReadings;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateSensorReadings(data: ISensorReadings[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.sensorReadings.push(data[i]);
      }
    }
  }
}
