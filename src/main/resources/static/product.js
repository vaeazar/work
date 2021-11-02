
function productList() {
  let xhr = new XMLHttpRequest();
  let productCategory = document.querySelector('#product-category').value;
  let productCategoryChild = document.querySelector('#product-category-child').value;
  let productId = document.querySelector('#product-id').value;
  let url = '/product-list';
  if (productCategory !== '') {
    url = url + '/' + productCategory;
    if (productCategoryChild !== '') {
      url = url + '/' + productCategoryChild;
      if (productId !== '') {
        url = url + '/' + productId;
      }
    }
  }

  xhr.open('GET', url, true);
  xhr.send();
  xhr.onload = () => {
    if (xhr.status == 200) {
      let jsonTemp = JSON.parse(xhr.response);
      let html = '';
      jsonTemp.data.forEach(function(e) {
        html += '<tr>';
        html += '  <td>';
        html += e.productId;
        html += '  </td>';
        html += '  <td>';
        html += e.productCategory;
        html += '  </td>';
        html += '  <td>';
        html += e.productCategoryChild;
        html += '  </td>';
        html += '  <td>';
        html += e.productName;
        html += '  </td>';
        html += '  <td>';
        html += e.productStatus;
        html += '  </td>';
        html += '</tr>';
      });
      document.querySelector('#productList').innerHTML = '';
      document.querySelector('#productList').innerHTML += html;
    } else {
      alert("통신 실패 재 요청 해주세요");
    }
  }
}

function productRegister() {
  let xhr = new XMLHttpRequest();
  let productCategory = document.querySelector('#product-category').value;
  let productCategoryChild = document.querySelector('#product-category-child').value;
  let productName = document.querySelector('#product-name').value;
  let productStatus = document.querySelector('#product-status').value;

  let data = {
    productCategory : productCategory,
    productCategoryChild : productCategoryChild,
    productName : productName,
    productStatus : productStatus,
  };
  xhr.open('POST', '/product-register', true);
  xhr.setRequestHeader('Content-Type', 'application/json');
  xhr.send(JSON.stringify(data));
  xhr.onload = () => {
    let jsonTemp = JSON.parse(xhr.response);
    if (xhr.status === 201) {
      alert('상품 등록 성공');
    } else {
      alert("통신 실패 재 요청 해주세요\nreason :: " + jsonTemp.message);
    }
  }
}

function productUnregister() {
  let xhr = new XMLHttpRequest();
  let productId = document.querySelector('#product-id').value;
  xhr.open('DELETE', '/product-unregister/' + productId, true);
  xhr.send();
  xhr.onload = () => {
    let jsonTemp = JSON.parse(xhr.response);
    if (xhr.status === 202) {
      alert('삭제 성공');
    } else {
      alert("통신 실패 재 요청 해주세요\nreason :: " + jsonTemp.message);
    }
  }
}

function productInfo() {
  let xhr = new XMLHttpRequest();
  let productId = document.querySelector('#product-id').value;
  let url = '/product-info';

  if (productId !== '') {
    url = url + '/' + productId;
  }
  xhr.open('GET', url, true);
  xhr.send();
  xhr.onload = () => {
    if (xhr.status == 200) {
      let jsonTemp = JSON.parse(xhr.response);
      let productInfo = jsonTemp.data;
      document.querySelector('#product-category').value = productInfo.productCategory;
      document.querySelector('#product-category-child').value = productInfo.productCategoryChild;
      document.querySelector('#product-name').value = productInfo.productName;
      document.querySelector('#product-status').value = productInfo.productStatus;
    } else {
      let jsonTemp = JSON.parse(xhr.response);
      alert("통신 실패 재 요청 해주세요\nreason :: " + jsonTemp.message);
    }
  }
}

function productChanger() {
  let xhr = new XMLHttpRequest();
  let productId = document.querySelector('#product-id').value;
  let productCategory = document.querySelector('#product-category').value;
  let productCategoryChild = document.querySelector('#product-category-child').value;
  let productName = document.querySelector('#product-name').value;
  let productStatus = document.querySelector('#product-status').value;

  let data = {
    productId : productId,
    productCategory : productCategory,
    productCategoryChild : productCategoryChild,
    productName : productName,
    productStatus : productStatus,
  };

  xhr.open('PUT', '/product-changer', true);
  xhr.setRequestHeader('Content-Type', 'application/json');
  xhr.send(JSON.stringify(data));
  xhr.onload = () => {
    let jsonTemp = JSON.parse(xhr.response);
    if (xhr.status === 200) {
      alert('상품 수정 성공');
    } else {
      alert("통신 실패 재 요청 해주세요\nreason :: " + jsonTemp.message);
    }
  }
}