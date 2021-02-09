import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ShipcommanderTestModule } from '../../../test.module';
import { ContainerUpdateComponent } from 'app/entities/container/container-update.component';
import { ContainerService } from 'app/entities/container/container.service';
import { Container } from 'app/shared/model/container.model';

describe('Component Tests', () => {
  describe('Container Management Update Component', () => {
    let comp: ContainerUpdateComponent;
    let fixture: ComponentFixture<ContainerUpdateComponent>;
    let service: ContainerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ShipcommanderTestModule],
        declarations: [ContainerUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(ContainerUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContainerUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContainerService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Container(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Container();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
