import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ShipcommanderTestModule } from '../../../test.module';
import { PortDetailComponent } from 'app/entities/port/port-detail.component';
import { Port } from 'app/shared/model/port.model';

describe('Component Tests', () => {
  describe('Port Management Detail Component', () => {
    let comp: PortDetailComponent;
    let fixture: ComponentFixture<PortDetailComponent>;
    const route = ({ data: of({ port: new Port(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShipcommanderTestModule],
        declarations: [PortDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(PortDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PortDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load port on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.port).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
